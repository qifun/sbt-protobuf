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
import java.io.File

trait ProtobufKeys {
  final val protobufOptions = SettingKey[Seq[String]](
    "protobuf-options",
    "Additional command-line options to compile google protobuf files.")

  final val protobuf = TaskKey[Seq[File]]("protobuf", "Compile google protobuf files.")

  final val protobufCommand = SettingKey[String]("protobuf-command", "The command to compile google protobuf files.")
}

final object ProtobufKeys extends ProtobufKeys
