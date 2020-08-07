/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/**
 * Type handlers.
 * 类型转换模块
 * 1 为了简化配置文件提供了别名机制，该机制是类型转换模块的主要功能之一。
 * 2 实现 JDBC 类型与 Java 类型之间的转换，该功能在
 *   2.1 为SQL语句绑定实参：为 SQL 语句绑定实参时，会将数据由 Java 类型转换成 JDBC 类型。
 *   2.2 映射查询结果集：将数据由 JDBC 类型转换成 Java 类型
 */
package org.apache.ibatis.type;
