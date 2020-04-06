

# Test

mvn de.mhus.mvn.plugin:versioning-maven-plugin:1.0.0-SNAPSHOT:validate-no-snapshots

mvn de.mhus.mvn.plugin:versioning-maven-plugin:1.0.0-SNAPSHOT:modify-versions

# Modify Versions Mojo

Execute the mojo before you change all the versions in the version defining project (e.g. parent project). It Takes the conrent versions and the version.xml file to generate a new set of dependency versions and properties values.

## Versions File Format


Default file: ../versions.xml

Options:

* - Last Minor version (remove SNAPSHOT and minus one minor, 0 hotfix)
* + Next Minor version (only remove SNAPSHOT, 0 hotfix)

* - 1.0.0 - Last Major version 
* +1.0.0 - Next Major
* +1.0, + 0.1, +0.1.0 - Next Minor
* + 0.0.1 - Next Hotfix
* 1.0.0 - Explicit version

Format conrently only: Major.Minor.Hotfix-SNAPSHOT


```
<versions>
    <properties>
        <mhus-parent.version       >+</mhus-parent.version>
        <mhus-lib.version          >-</mhus-lib.version>
        <mhus-ports.version        >-</mhus-ports.version>
        <mhus-ports-vaadin.version >-</mhus-ports-vaadin.version>
        <mhus-osgi-tools.version   >-</mhus-osgi-tools.version>
        <mhus-vaadin.version       >+</mhus-vaadin.version>
        <mhus-mongo.version        >+</mhus-mongo.version>
        <mhus-transform.version    >+</mhus-transform.version>
        <mhus-osgi-servlets.version>+</mhus-osgi-servlets.version>
        <mhus-crypt.version        >+</mhus-crypt.version>
        <mhus-sop.version          >+</mhus-sop.version>
        <cherry-web.version        >+</cherry-web.version>
        <cherry-reactive.version   >+</cherry-reactive.version>
        <cherry-vault.version      >+</cherry-vault.version>
  <properties>
  <dependencies>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-parent</artifactId>
           <version>${mhus-parent.version}</version>
       </dependency>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-annotations</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-core</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-j2ee</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-jms</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-persistence</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
         <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-jpa</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
        <dependency>
           <groupId>de.mhus.lib</groupId>
           <artifactId>mhu-lib-mongo</artifactId>
           <version>${mhus-lib.version}</version>
       </dependency>
  </dependencies>
</versions>
```
