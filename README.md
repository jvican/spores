# Spores

[![Build Status](https://platform-ci.scala-lang.org/api/badges/scalacenter/spores/status.svg)](https://platform-ci.scala-lang.org/scalacenter/spores)
[![Gitter](https://badges.gitter.im/scalacenter/spores.svg)](https://gitter.im/scalacenter/spores?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Maven Central](https://img.shields.io/maven-central/v/ch.epfl.scala/spores_2.11.svg)][search.maven]

**[User Documentation](http://scalacenter.github.io/spores/spores.html).**

Scala Spores, safe mobile closures: [SIP-21](http://docs.scala-lang.org/sips/pending/spores.html).
  
Spores is an extension of the Scala compiler that enables a safer use of closures
in concurrent and distributed environments. It allows developers to guarantee properties
of functions based on types, having more control over the function's environment.

Spores comes with a transitive checker to ensure that captured types can be
serialized by the JVM (Java serialization). For more information, check the
[motivation and the getting-started](https://scalacenter.github.io/spores/java-serialization.html) guide.

## Add to your project

```scala
libraryDependencies += "ch.epfl.scala" %% "spores" % "0.4.3"
addCompilerPlugin("ch.epfl.scala" %% "spores-serialization" % "0.4.3")
```

[search.maven]: http://search.maven.org/#search|ga|1|ch.epfl.scala.spores
