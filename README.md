## AE2CGroceryStore (PRJ301_GroupProject)

A Java web application (Maven WAR) for a grocery store. This repository contains the source code, SQL schema/data, and build artifacts for the AE2CGroceryStore project.

This README explains how to build, configure, and run the application locally and how to configure the database connection.

## Quick summary

- Language: Java
- Build system: Maven
- Packaging: WAR (deploy to an application server such as Apache Tomcat)
- Database: Microsoft SQL Server

## Project structure (important paths)

- `AE2CGroceryStore/` — main webapp project (source)
  - `src/main/java/` — Java sources (controllers, DAOs, models, utils)
  - `src/main/webapp/` — JSPs and web resources
  - `pom.xml` — Maven build file
  - `src/main/java/dbconnect/DBContext.java` — database connection wrapper (edit DB connection here)
- `Database/` — SQL scripts
  - `database_store.sql` — schema + seed data (use to create the GroceryStore DB)
  - `database_data_no1.sql` — optional data dump
  - `database_dropAll.sql` — drop tables / cleanup

## Prerequisites

- Java JDK (recommended JDK 11 or newer). The project uses older source/target settings in `pom.xml` but the MS SQL JDBC driver and some dependencies work best on newer JDKs.
- Apache Maven (3.6+)
- Microsoft SQL Server (local or accessible instance)
- An application server that supports WAR deployment (Apache Tomcat 9/10, or another Jakarta/Servlet container compatible with your Java version)

## Database setup

1. Create a new database named `GroceryStore` (or update the JDBC URL in `DBContext.java` to match your DB name).
2. Run the SQL script `Database/database_store.sql` to create tables and seed data. You can run it with SQL Server Management Studio (SSMS) or `sqlcmd`.

Example (using sqlcmd from PowerShell):

```powershell
sqlcmd -S localhost -U SA -P "<YourPassword>" -i "Database\database_store.sql"
```

3. The default DB connection values in the project are in `AE2CGroceryStore/src/main/java/dbconnect/DBContext.java`:

```java
private final String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=GroceryStore;encrypt=false";
private final String DB_USER = "sa";
private final String DB_PWD = ""; // fill the password
```

Edit these fields to match your SQL Server host, port, database name, and credentials. Alternatively, you can modify the class to read from environment variables or use a JNDI DataSource for production deployments.

Security note: credentials are currently hard-coded in `DBContext.java`. For production, avoid committing secrets into source control — use environment variables or a credentials store.

## Build

From the project root (`AE2CGroceryStore`), run:

```powershell
mvn clean package
```

This produces a WAR file in `target/`, for example `target/AE2CGroceryStore-1.0-SNAPSHOT.war`.

## Deploy

Option A — Deploy to Tomcat

1. Copy the WAR file to Tomcat's `webapps/` directory and start Tomcat. Tomcat will unpack and deploy the application.

Example (PowerShell):

```powershell
Copy-Item -Path "target\AE2CGroceryStore-1.0-SNAPSHOT.war" -Destination "C:\path\to\tomcat\webapps\"
# then start Tomcat via its service or startup script
```

Option B — Run from your IDE

Import the project into your IDE (NetBeans, IntelliJ IDEA, or Eclipse). Configure the server runtime (Tomcat) and run the project directly from the IDE.

## Default application entry

After deployment, open your browser and navigate to the app root (for example):

http://localhost:8080/AE2CGroceryStore-1.0-SNAPSHOT/

If the WAR filename differs, adjust the context path accordingly (or configure a context path in Tomcat).

## Troubleshooting

- JDBC Driver: The project depends on the Microsoft SQL Server JDBC driver (`mssql-jdbc`) declared in `pom.xml`. If you get ClassNotFoundException for `com.microsoft.sqlserver.jdbc.SQLServerDriver`, ensure dependencies are properly downloaded by running `mvn dependency:resolve`.
- Connection errors: check `DBContext.java` values and ensure SQL Server accepts TCP/IP connections (check SQL Server Configuration Manager) and the firewall allows port 1433.
- Java version issues: if you see compilation/runtime issues related to Jakarta/Java EE versions, try using a matching Tomcat version or adjust dependencies to align with your runtime.

## Code notes and next steps (suggestions)

- The `DBContext` class currently uses hard-coded connection properties. Consider refactoring to use environment variables or a JNDI DataSource (in `context.xml`) for safer configuration.
- Add integration tests or a small test harness to verify DB connectivity automatically.

## Authors / Contributors

- Original author: Nguyen Ho Phuoc An (CE190747) — see class headers
- Project workspace / grouping: PRJ301_GroupProject (AnRhino)

## License

This repository does not include an explicit license file. If you plan to publish or share this project, add a `LICENSE` file describing the intended license.

## Contact / Questions

If you need help running or configuring the project, open an issue in the repo or contact the project owner/maintainers.
