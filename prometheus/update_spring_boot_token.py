import getpass
import json
import os
import sys
from pathlib import Path
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen


PROMETHEUS_DIR = Path(__file__).resolve().parent
TOKEN_FILE = PROMETHEUS_DIR / "secrets" / "spring_boot_jwt"


def configured_secret_gid() -> int:
    env_value = os.environ.get("PROMETHEUS_SECRET_GID")
    if env_value is None:
        env_file = PROMETHEUS_DIR.parent / ".env"
        if env_file.exists():
            for line in env_file.read_text(encoding="utf-8").splitlines():
                key, separator, value = line.partition("=")
                if separator and key.strip() == "PROMETHEUS_SECRET_GID":
                    env_value = value.strip()
                    break

    try:
        return int(env_value) if env_value else 1000
    except ValueError:
        raise SystemExit("PROMETHEUS_SECRET_GID must be a numeric group ID.") from None


def main() -> None:
    email = input("ADM email: ").strip()
    password = getpass.getpass("ADM password: ")
    if not email or not password:
        raise SystemExit("Email and password are required.")

    request = Request(
        "http://localhost:8080/auth/login",
        data=json.dumps({"email": email, "password": password}).encode(),
        headers={"Content-Type": "application/json"},
        method="POST",
    )

    try:
        with urlopen(request, timeout=10) as response:
            payload = json.load(response)
    except HTTPError as error:
        raise SystemExit(f"Login failed: HTTP {error.code}.") from None
    except URLError as error:
        raise SystemExit(f"Could not reach the application: {error.reason}.") from None
    except (UnicodeDecodeError, json.JSONDecodeError):
        raise SystemExit("The login endpoint returned an invalid response.") from None

    token = payload.get("token") if isinstance(payload, dict) else None
    if not isinstance(token, str) or not token.strip():
        raise SystemExit("The login response did not contain a JWT token.")

    secret_gid = configured_secret_gid()
    if secret_gid != os.getgid():
        raise SystemExit(
            f"Set PROMETHEUS_SECRET_GID to your primary group ID ({os.getgid()}) in .env."
        )

    token_dir = TOKEN_FILE.parent
    token_dir.mkdir(mode=0o710, parents=True, exist_ok=True)
    os.chmod(token_dir, 0o710)

    fd = os.open(TOKEN_FILE, os.O_WRONLY | os.O_CREAT | os.O_TRUNC, 0o600)
    with os.fdopen(fd, "w", encoding="utf-8") as token_file:
        os.fchmod(token_file.fileno(), 0o640)
        token_file.write(token.strip())
        token_file.write("\n")

    print(f"JWT saved to {TOKEN_FILE}; it was not added to Git.")


if __name__ == "__main__":
    main()
