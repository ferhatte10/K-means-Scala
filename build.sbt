name := "K-means-Scala"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "org.bytedeco" % "javacpp"   % "1.5.3"       classifier "macosx-x86_64" classifier "windows-x86_64" classifier "linux-x86_64" classifier "linux-arm64" classifier "linux-ppc64le" classifier "android-arm64" classifier "ios-arm64",
  "org.bytedeco" % "openblas"  % "0.3.9-1.5.3" classifier "macosx-x86_64" classifier "windows-x86_64" classifier "linux-x86_64" classifier "linux-arm64" classifier "linux-ppc64le" classifier "android-arm64" classifier "ios-arm64",
  "org.bytedeco" % "arpack-ng" % "3.7.0-1.5.3" classifier "macosx-x86_64" classifier "windows-x86_64" classifier "linux-x86_64" classifier "linux-arm64" classifier "linux-ppc64le" classifier ""
)

libraryDependencies  ++= Seq(
  "org.scalanlp" %% "breeze" % "1.1",
  "org.scalanlp" %% "breeze-natives" % "1.1",
  "org.scalanlp" %% "breeze-viz" % "1.1"
)


//libraryDependencies += "co.theasi" % "plotly_2.11" % "0.2.0"
//libraryDependencies += "com.github.haifengl" %% "smile-scala" % "2.6.0"
//libraryDependencies += "com.github.haifengl" % "smile-core" % "2.6.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime