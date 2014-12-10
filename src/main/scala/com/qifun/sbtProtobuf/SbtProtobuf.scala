/*
 * sbt-protobuf
 * Copyright 2014 深圳岂凡网络有限公司 (Shenzhen QiFun Network Corp., LTD)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qifun.sbtProtobuf

import sbt._
import Keys._
import ProtobufKeys._
import ProtobufConfigurations._
import java.io.File

final object SbtProtobuf {
  private[sbtProtobuf] final def protobufSettings(
    protobufConfiguration: Configuration,
    injectConfiguration: Configuration) = {
    protobuf in injectConfiguration := {
      val protobufStreams = (streams in protobufConfiguration).value
      val logger = protobufStreams.log

      val cachedTranfer =
        FileFunction.cached(
          protobufStreams.cacheDirectory / ("protobuf_" + scalaVersion.value),
          inStyle = FilesInfo.lastModified,
          outStyle = FilesInfo.exists) { (in: Set[File]) =>
            val outputDir = (sourceManaged in protobufConfiguration).value
            IO.delete(outputDir)
            IO.createDirectory(outputDir)
            val processBuilder =
              Seq[String]((protobufCommand in protobufConfiguration).value,
                "-cp", (managedClasspath in protobufConfiguration).value.map(_.data).mkString(File.pathSeparator)) ++
                (protobufOptions in protobufConfiguration).value ++ in.toSeq.map(_.toString)
            logger.info(processBuilder.mkString("\"", "\" \"", "\""))
            processBuilder !< logger match {
              case 0 =>
                logger.info("Generate success!")
                (outputDir ** ("*.scala")).get.toSet
              case result =>
                throw new MessageOnlyException("Generate failed!")
            }
          }
      cachedTranfer((sources in protobufConfiguration).value.toSet).toSeq
    }
  }

  private[sbtProtobuf] final val baseProtobufSettings =
    Defaults.configTasks ++
      Defaults.configPaths ++
      Classpaths.configSettings ++
      Defaults.packageTaskSettings(
        packageBin,
        Defaults.sourceMappings) ++
        Seq(
          managedClasspath := Classpaths.managedJars(Compile, classpathTypes.value, update.value),
          internalDependencyClasspath := {
            ((for {
              ac <- Classpaths.allConfigs(configuration.value)
              if ac != configuration.value
              sourcePaths <- (sourceDirectories in (thisProjectRef.value, ac)).get(settingsData.value).toList
              sourcePath <- sourcePaths
            } yield sourcePath)).classpath
          },
          unmanagedSourceDirectories := Seq(sourceDirectory.value),
          includeFilter in unmanagedSources := new FileFilter {
            override final def accept(file: File) =
              file.isFile && file.getPath.toString.endsWith(".proto")
          })

  private[sbtProtobuf] final def injectSettings(
    protobufConfiguration: Configuration,
    injectConfiguration: Configuration) = {
    Seq(sourceGenerators in injectConfiguration <+= protobuf in injectConfiguration,
      protobufOptions in protobufConfiguration := Seq(
        "net.sandrogrzicic.scalabuff.compiler.ScalaBuff",
        "--scala_out=" + (sourceManaged in protobufConfiguration).value))
  }
}
