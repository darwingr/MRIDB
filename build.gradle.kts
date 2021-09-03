plugins {
  java
  `java-library`
  application
}

repositories {
  mavenCentral()
}

group = "ca.darweb"
version = "0.2"

dependencies {
  // https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8
  implementation("com.oracle.database.jdbc:ojdbc8:21.3.0.0")

  testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
}

sourceSets {
  main {
    java {
      setSrcDirs(listOf("src"))
    }
    resources {
      setSrcDirs(listOf("res"))
    }
  }

  test {
    java {
      setSrcDirs(listOf("test"))
    }
  }
}

tasks.test {
  useJUnitPlatform()
}

//tasks.withType(JavaCompile::class) {
//  options.compilerArgs.add("-Xlint:deprecation")
//  options.compilerArgs.add("-Xlint:unchecked")
//}

application {
  // Define the main class for the application.
  mainClass.set("game.GameManager")
  //executableDir = "custom_bin_dir"
}
