/**
 *    Copyright 2009-2020 the original author or authors.
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
 * Bings mapper interfaces with mapped statements.
 * Bind xml with mapper interface
 * Bind sql with xml
 * 将用户自定义的 Mapper 接口与映射配置文件关联起来，
 * 系统可以通过调用自定义 Mapper 接口中的方法执行相应的 SQL 语句完成数据库操作
 * MyBatis 初始化过程中，
 *
 * 1 加载 mybatis-config.xml 配置文件、映射配置文件以及 Mapper 接口中的注解信息，
 * 解析后的配置信息会形成相应的对象并保存到 Configuration 对象，如
 * <resultMap>节点(即 ResultSet 的映射规则) 会被解析成 ResultMap 对象。
 * <result> 节点(即属性映射)会被解析成 ResultMapping 对象。
 *
 * 2 利用该 Configuration 对象创建 SqlSessionFactory对象。
 * 待 MyBatis 初始化之后，
 * 开发人员可以通过初始化得到 SqlSessionFactory 创建 SqlSession 对象并完成数据库操作。
 */
package org.apache.ibatis.binding;
