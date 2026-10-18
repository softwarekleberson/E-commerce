---
name: document-project-architecture
description: Document or update this project's architecture from source code, including documenting a newly added module. Use when mapping modules and folders, explaining use cases, or keeping module READMEs and architecture indexes current.
metadata:
  short-description: Document project architecture
---

# Document Project Architecture

Create accurate, navigable Markdown documentation for this repository. Explain what the code does and how its parts collaborate. Use the README, architecture documents, and ADRs as context, not as proof that every described design is implemented.

## Workflow

1. Read repository guidance (`AGENTS.md` and relevant `.agents/` instructions), the root README, `pom.xml`, existing architecture docs, and relevant ADRs before writing.
2. Inventory source directories, modules, layers, cross-cutting components, configuration, persistence, migrations, and tests. Exclude generated/build output. When a new module is in scope, compare the current module directories with existing module READMEs and architecture indexes to find missing documentation.
3. Trace each in-scope module from entry points through application orchestration and domain behavior to ports/adapters and persistence or external systems. Inspect implementations, callers, and tests where they clarify behavior.
4. Identify use cases from application/use-case classes and exposed entry points. For each, document the trigger, input, main steps, rules, collaborators, side effects, output/failure paths, and relevant tests only where supported by source evidence.
5. Write in Brazilian Portuguese by default, matching the user's language. Keep source identifiers unchanged and explain specialized terms briefly.
6. Organize docs so a reader can navigate from the system overview to modules, folders/layers, and use cases. Keep a `README.md` inside every in-scope module directory as its local guide, and link it from the project-level architecture index. Prefer focused Markdown pages with relative links. Update canonical existing docs instead of duplicating them.
7. Link claims to source files (and line anchors where useful), tests, related modules, ADRs, and docs. Check paths, relative links, and claims against the working tree.
8. Separate intended architecture (README/ADRs) from observed implementation. Label gaps, inconsistencies, unknowns, and assumptions; do not invent behavior. State when no matching use case or test was found.
9. If asked to document a subset, keep the scope to it and its relevant dependencies. When refreshing docs, preserve valid material and replace stale claims.

## Suggested documentation shape

Follow existing repository conventions. For this project, use `doc/docs/architecture.md` as the system index and overview, with one page per business module under `doc/docs/architecture/`. Add cross-cutting pages only for concerns that span modules. Each module page should explain its directory tree, layers, important types, dependencies, and discovered use cases. Do not create empty pages or speculative use cases. Summarize folders by responsibility; do not mechanically describe trivial getters or generated files.

## When a module is introduced

When asked to document a new module, or when inventory reveals a module without documentation:

1. Confirm its source/package location and inspect its actual folders, entry points, application/use-case classes, domain types, adapters, configuration, persistence, events, and tests.
2. Create or update `<module>/README.md` in that module's source directory. Explain responsibility, actual folder structure, architecture/dependency direction, discovered use cases and their evidenced flows, cross-module dependencies, and tests. Do not copy a neighboring module's structure if the new module differs.
3. Add or update the matching page in the project's architecture documentation when that convention exists.
4. Update the central module index and any README/module inventory that enumerates modules. Add links in both directions where readers need to move between the local module guide and central architecture docs.
5. Verify every relative link and confirm the module name, path, and described classes exist. Mark unanswered questions instead of filling them with assumptions.

For this repository, the expected locations are `src/main/java/com/cleancode/ecommerce/<module>/README.md`, `doc/docs/architecture/<module>.md`, and the module index in `doc/docs/architecture.md`. Also check the root `README.md` for a module inventory that needs updating.

## Module checklist

- Business responsibility and boundaries grounded in implementation.
- Actual folder structure and each folder's purpose.
- Important classes and dependency direction between layers.
- HTTP or other entry points, contracts, adapters, repositories/gateways, events, and external dependencies where present.
- Use-case execution flows, validation, domain rules, persistence, emitted events, and evidenced failure paths.
- Relevant tests and differences between documented intent and current code.

## Accuracy rules

- Source code and executable configuration are evidence of current behavior; tests show covered behavior, not every possible behavior.
- Treat README, diagrams, ADRs, and comments as design intent unless corroborated by implementation.
- Distinguish facts, design intent, and open questions explicitly.
- Keep descriptions concise but useful for locating a rule or tracing a request.
- Do not modify application code during a documentation task unless code changes are also requested.
