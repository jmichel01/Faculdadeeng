# Contributing to TaskFlow

Thank you for your interest in contributing to **TaskFlow**! This document outlines the guidelines for contributing to this academic project.

---

## 📋 Development Workflow

1. **Fork** the repository on GitHub.
2. **Clone** your fork locally.
3. Create a **new branch** for your feature or bugfix:
   ```bash
   git checkout -b feat/your-feature-name
   ```
4. Make your changes following our **code standards** below.
5. **Commit** using semantic commit messages (see below).
6. **Push** your branch to your fork and open a **Pull Request**.

---

## 🧑‍💻 Code Standards

- Use **Java 17** features where appropriate.
- Follow **SOLID principles** and clean architecture patterns.
- All public methods and classes must have **Javadoc** comments.
- DTOs must include **JSR-380 validation** annotations where applicable.
- Keep controllers thin — business logic belongs in the **service layer**.

---

## ✅ Semantic Commit Convention

We follow [Conventional Commits](https://www.conventionalcommits.org/) format:

| Prefix | Use Case |
| :--- | :--- |
| `feat:` | New feature |
| `fix:` | Bug fix |
| `docs:` | Documentation changes |
| `refactor:` | Code refactoring without behavior change |
| `test:` | Adding or updating tests |
| `chore:` | Maintenance tasks (e.g. dependency updates) |
| `ci:` | CI/CD pipeline changes |
| `config:` | Configuration changes |

---

## 🧪 Running Tests

Before opening a PR, run the full test suite:

```bash
mvn clean test
```

All **12 existing tests** must pass. Add new tests for any new functionality.

---

## 📄 License

By contributing, you agree that your contributions will be academic and educational in nature.
