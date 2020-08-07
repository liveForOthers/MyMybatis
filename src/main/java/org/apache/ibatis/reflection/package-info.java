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
 * Reflection utils.
 * 背景: 反射功能强大，但是开发人员素质有限，直接使用jdk反射接口不能确保高质量反射代码
 * 对 Java 原生的反射进行了良好的封装，提了更加简洁易用的 API，方便上层使调用，
 * 并且对反射操作进行了一系列优化，例如缓存了类的元数据，提高了反射操作的性能。
 */
package org.apache.ibatis.reflection;
