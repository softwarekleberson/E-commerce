---
name: clean-ecommerce-review
description: Review Clean Ecommerce code for security, performance, errors, test coverage, and deploy readiness; use for explicit code review or readiness audits.
metadata:
  short-description: Review security, quality, tests, and deploy readiness
---

# Clean Ecommerce Code Review

Use this skill when asked to review code or assess the project's readiness for deployment. Review the requested scope; if the user asks for the whole project, inspect all relevant source, configuration, migrations, and tests rather than assuming a small diff is complete. Follow the architecture guidance in `skills/clean-ecommerce/SKILL.md` and `doc/docs/architecture.md`, while treating implementation as the source of truth.

## Review method

- Trace exposed behavior through controller, application/use case, domain, and infrastructure. Inspect security configuration, persistence queries/mappers, migrations, dependency declarations, and deployment configuration when relevant.
- Base each finding on concrete code or command output. Include file and line when available, affected behavior/process, impact, and a specific remediation. Do not report hypothetical vulnerabilities as confirmed findings; label unverified concerns as risks to investigate.
- Classify each finding as **Crítico**, **Possivelmente preocupante**, or **Sugestão**. Critical means a credible severe security/data-loss/outage issue or a blocker for the requested release. Concerning means meaningful risk or likely production defect. Suggestion means a quality improvement without a demonstrated material failure. Avoid duplicating one root cause across sections.
- Protect secrets during inspection. Report the file and type of exposure without copying credential values into the report. Do not print `.env` contents, private keys, tokens, or personal data. Distinguish sample/example values from live credentials; avoid modifying or deleting files during the audit unless asked.
- Report each dimension even when no finding is confirmed. State review limits and checks that could not be completed.

## #1 Security

Check for accidentally exposed private information and committed secrets or real personal data; inspect tracked files and relevant configuration without revealing values. Review authentication and authorization coverage for routes, roles, and data ownership, including Spring Security configuration and JWT handling. Check for hardcoded credentials, weak secret management, injection risks (including unsafe SQL/JPA queries), XSS in rendered or reflected content, CSRF/session mistakes, path traversal, unsafe deserialization, insecure redirects, and other applicable OWASP Top 10 issues. Consider CORS, validation, password storage, and sensitive data in logs/responses where relevant.

Do not assume an endpoint is protected because authentication exists somewhere in the application. Trace route matchers and method-level checks, then compare them with intended access rules in the code and documentation.

## #2 Performance

Look for unbounded reads, missing pagination/streaming, inefficient algorithms, N+1 or repeated database queries, unbounded caches, resource leaks, blocking work on request paths, and concurrency hazards in shared mutable state. For Spring/JPA, inspect fetch behavior and transaction boundaries as well as query shape. Describe the conditions and likely scale at which a bottleneck matters; do not call ordinary synchronous work a defect without evidence.

## #3 Errors and production behavior

Trace normal and failure paths for uncaught exceptions, invalid input, missing records, I/O/network/parsing failures, divide-by-zero or null cases, and inconsistent error responses. Check whether operationally important failures can be diagnosed without exposing sensitive details. Review configuration assumptions such as required environment variables, relative paths, encoding, filesystem permissions, and differences between local and production execution. Check dependency compatibility against the project's Java 25 and Spring Boot 3.5 declarations; distinguish managed Maven versions from dependencies explicitly pinned in `pom.xml`.

## #4 Tests

1. Check whether Java tests exist under `src/test/java`; inspect their scope and run the Maven test suite with `./mvnw test` (or `mvnw.cmd test` on Windows) when the environment permits.
2. Identify important uncovered behavior, prioritizing security boundaries, domain invariants/state transitions, checkout/payment/stock flows, and error paths implicated by findings.
3. Add focused tests for critical uncovered behavior to the appropriate existing test class, or create a properly located test class if none exists. Keep the additions within the review scope and consistent with existing JUnit/Spring test conventions. Do not rewrite unrelated tests or weaken assertions to obtain a green run.
4. Run the relevant tests and the full suite after adding tests. Report the exact commands, pass/fail outcome, and test count as **X/Y testes passando** only when the runner output supports that count. If execution is blocked or a baseline failure exists, state that clearly and do not claim a pass ratio that was not established.

## #5 Deploy readiness

Perform this section only if no **Crítico** findings were found in sections #1–#4. If a critical finding exists, explicitly state that deploy checks were skipped pending resolution.

- Inspect `git status` and local commits versus the available `origin` tracking ref (for example `git log --oneline @{u}..HEAD` and `git log --oneline HEAD..@{u}` when an upstream is configured). Do not fetch, pull, push, or change branches as part of the review. If no current remote tracking ref exists, say synchronization could not be verified; do not imply the remote is current.
- Check whether dependencies and toolchain are declared reproducibly in `pom.xml` and Maven Wrapper configuration. Note both explicit versions and versions inherited from Spring Boot dependency management; do not recommend pinning every transitive dependency without a concrete reason.
- Check whether required environment variables and runtime configuration are documented, including safe example/default values and local setup guidance. Never expose secret values in the report.

## Report format

Write the report in Portuguese unless the user asks otherwise. Organize it as follows:

1. **#1 Segurança**
2. **#2 Performance**
3. **#3 Erros**
4. **#4 Testes**
5. **#5 Deploy** (or explain that it was skipped because a critical finding exists)

For every finding, show the severity, affected file/process, evidence and impact, and a concrete suggested action. If a dimension has no confirmed findings, say so and mention relevant limits. End with an **Resumo Executivo** table containing the findings and severities, then one final line stating whether the code is ready for deploy or what must be resolved first. Readiness is a review conclusion, not an approval to deploy or publish anything.
