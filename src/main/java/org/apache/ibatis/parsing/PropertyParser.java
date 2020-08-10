/**
 *    Copyright 2009-2016 the original author or authors.
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
package org.apache.ibatis.parsing;

import java.util.Properties;

/**
 * @author Clinton Begin
 * @author Kazuki Shimizu
 * 职责 实现动态key替换
 */
public class PropertyParser {

  private static final String KEY_PREFIX = "org.apache.ibatis.parsing.PropertyParser.";
  /**
   * The special property key that indicate whether enable a default value on placeholder.
   * <p>
   *   The default value is {@code false} (indicate disable a default value on placeholder)
   *   If you specify the {@code true}, you can specify key and default value on placeholder (e.g. {@code ${db.username:postgres}}).
   * </p>
   * @since 3.4.2
   */
  public static final String KEY_ENABLE_DEFAULT_VALUE = KEY_PREFIX + "enable-default-value";

  /**
   * The special property key that specify a separator for key and default value on placeholder.
   * <p>
   *   The default separator is {@code ":"}.
   * </p>
   * @since 3.4.2
   */
  public static final String KEY_DEFAULT_VALUE_SEPARATOR = KEY_PREFIX + "default-value-separator";

  private static final String ENABLE_DEFAULT_VALUE = "false";
  private static final String DEFAULT_VALUE_SEPARATOR = ":";

  // 构造方法，修饰符为 private ，禁止构造 PropertyParser 对象，因为它是一个静态方法的工具类。
  private PropertyParser() {
    // Prevent Instantiation
  }

  /**
   * 基于 variables 变量，替换 string 字符串中的动态属性，并返回结果
   *
   * @param string  目标字符串
   * @param variables  替换key value集合对象
   * @return 替换后的字符串
   */
  public static String parse(String string, Properties variables) {
    // 创建 VariableTokenHandler 对象
    VariableTokenHandler handler = new VariableTokenHandler(variables);
    // 创建 GenericTokenParser 对象。
    // openToken = ${ ，closeToken = } 也就是根据此标准解析替换 ${key}
    GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
    return parser.parse(string); // 执行解析替换
  }

  private static class VariableTokenHandler implements TokenHandler {
    private final Properties variables;
    /**
     * 是否开启默认值功能。默认为 {@link #ENABLE_DEFAULT_VALUE} 可配置properties 进行修改
     */
    private final boolean enableDefaultValue;
    /**
     * 默认值的分隔符。默认为 {@link #DEFAULT_VALUE_SEPARATOR} ，即 ":" 。 可配置properties 进行修改
     */
    private final String defaultValueSeparator;

    private VariableTokenHandler(Properties variables) {
      this.variables = variables;
      this.enableDefaultValue = Boolean.parseBoolean(getPropertyValue(KEY_ENABLE_DEFAULT_VALUE, ENABLE_DEFAULT_VALUE));
      this.defaultValueSeparator = getPropertyValue(KEY_DEFAULT_VALUE_SEPARATOR, DEFAULT_VALUE_SEPARATOR);
    }

    private String getPropertyValue(String key, String defaultValue) {
      return (variables == null) ? defaultValue : variables.getProperty(key, defaultValue);
    }

    @Override
    public String handleToken(String content) {
      if (variables != null) {
        String key = content;
        if (enableDefaultValue) {
          // 查找默认值 默认值可在配置的key后加分隔符配置如 key:0 对应key如property中未配置 配置为默认值0
          final int separatorIndex = content.indexOf(defaultValueSeparator);
          String defaultValue = null;
          if (separatorIndex >= 0) { // 存在默认值的情况
            key = content.substring(0, separatorIndex);
            defaultValue = content.substring(separatorIndex + defaultValueSeparator.length());
          }
          // 如key不在property中则 返回默认值
          if (defaultValue != null) {
            return variables.getProperty(key, defaultValue);
          }
        }
        // 未开启默认值功能 或默认值不存在，直接替换
        if (variables.containsKey(key)) {
          return variables.getProperty(key);
        }
      }
      // 未配置properties 不执行替换
      return "${" + content + "}";
    }
  }

}
