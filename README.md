# Atos Intake 4 Automation Testing

[![Maven](https://img.shields.io/badge/build-Maven-blue)](https://maven.apache.org/) [![Java](https://img.shields.io/badge/language-Java-orange)](https://www.java.com/)

This project contains automated tests for the Atos Internship Intake 4 using Java, Maven, and TestNG. It covers:

- End-to-end user flows
- Regression and smoke tests
- Data-driven and UI validation
- Custom reporting (ExtentReports)

## Features

- Modular test suites (TestNG XML)
- Configurable environments via `Config.properties`
- HTML/XML/Extent test reports
- Easy integration with CI/CD pipelines

## Project Structure

- `src/main/java/` - Main source code (if any)
- `src/test/java/` - Test code
- `src/main/resources/` - Main resources
- `src/test/resources/` - Test resources
- `target/` - Build output, reports, and generated files
- `TestRunner/` - TestNG suite XML files
- `pom.xml` - Maven build configuration

## Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

## Setup & Configuration

1. Clone the repository:
   ```
   git clone <repo-url>
   ```
2. Configure environment variables in `target/classes/Config.properties` (e.g., base URL, credentials).
3. (Optional) Update TestNG suite files in `TestRunner/` for custom test runs.

## Build Instructions

To build the project, run:

```
mvn clean install
```

## Running Tests

To execute all TestNG suites:

```
mvn test
```

Or run a specific suite:

```
mvn -DsuiteXmlFile=TestRunner/regression.xml test
```

## Test Reports

- HTML and XML reports: `target/`, `test-output/`
- ExtentReports: `test-output/`
- Example: `test-output/ExtentReport_<date>.html`

## Troubleshooting

- Ensure Java and Maven are installed and on your PATH.
- Clean previous builds if you encounter issues:
  ```
  mvn clean
  ```
- Check `Config.properties` for correct settings.
- Review logs in `test-output/` for errors.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit your changes
4. Submit a pull request

## Contact

For questions or support, contact the project maintainer or Atos Internship coordinator.

## License

This project is for educational and internal use at Atos. Contact the project owner for licensing details.

---
_Last updated: September 29, 2025_
