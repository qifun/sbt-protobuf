# sbt-protobuf

<div align="right"><a href="https://travis-ci.org/qifun/sbt-protobuf"><img alt="Build Status" src="https://travis-ci.org/qifun/sbt-protobuf.png?branch=master"/></a></div>

**sbt-protobuf** is a [Sbt](http://www.scala-sbt.org/) plugin to compile google protobuf files.

This Plugin use the [ScalaBuff](https://github.com/SandroGrzicic/ScalaBuff) library to compile protobuf files.

## Usage

### Step 1: Install `sbt-protobuf` into your project

Add the following line to your `project/plugins.sbt`:

    addSbtPlugin("com.qifun" % "sbt-protobuf" % "0.1.0")

And add settings to your `build.sbt`:

    libraryDependencies += "net.sandrogrzicic" %% "scalabuff-compiler" % "1.3.8"

    libraryDependencies += "net.sandrogrzicic" %% "scalabuff-runtime" % "1.3.8"

### Step 2: Put your protobuf files at `src/protobuf/yourPackage/YourProto.proto`

``` protobuf
message Test1 {
  required int32 id = 1;
  required string name = 2;
  optional string email = 3;
}

```

### Step 3: Just compile it!

run `compile` command in sbt.

