/**
 *    Copyright 2009-2019 the original author or authors.
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

/**
 * @author Clinton Begin
 * 抽象出 openToken，closeToken 可以支持多协议格式的串
 * 抽象出 TokenHandler 支持多个处理器
 */
public class GenericTokenParser {
  // todo 思考 如何做到通用替换的  1 o,c token  2 handler抽象
  private final String openToken;
  private final String closeToken;
  private final TokenHandler handler;

  public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
    this.openToken = openToken;
    this.closeToken = closeToken;
    this.handler = handler;
  }

  public String parse(String text) {
    if (text == null || text.isEmpty()) {
      return "";
    }
    // search open token 寻找开始的 openToken 的位置
    int start = text.indexOf(openToken);
    // 不存在 说明无需替换直接返回
    if (start == -1) {
      return text;
    }
    char[] src = text.toCharArray();
    int offset = 0; // 起始查找位置
    final StringBuilder builder = new StringBuilder(); // 结果存储对象
    StringBuilder expression = null;
    while (start > -1) {
      // 转义字符 前面多了一个 '\'
      if (start > 0 && src[start - 1] == '\\') {
        // this open token is escaped. remove the backslash and continue.
        // openToken 前面一个位置是 \ 转义字符，所以忽略 \ 之前的有效串加入结果集
        // 并且忽略 "\${"
        builder.append(src, offset, start - offset - 1).append(openToken);
        offset = start + openToken.length(); // 更新偏移量
      } else {
        // found open token. let's search close token.
        // 创建/重置 expression 对象
        if (expression == null) {
          expression = new StringBuilder();
        } else {
          expression.setLength(0); // learn: set length zero to reset stringbuilder object.
        }
        // 将替换符前的串加入结果集
        builder.append(src, offset, start - offset);
        offset = start + openToken.length(); // 更新偏移量 偏移量随着结果集变化更新
        int end = text.indexOf(closeToken, offset); // 找尾巴替换符
        while (end > -1) {
          // 尾巴替换符前是转移字符
          if (end > offset && src[end - 1] == '\\') {
            // this close token is escaped. remove the backslash and continue.
            // 忽略转义字符的写替换key
            expression.append(src, offset, end - offset - 1).append(closeToken);
            offset = end + closeToken.length();
            end = text.indexOf(closeToken, offset);
          } else {
            // 找到有效尾巴替换符 写替换key 并结束尾巴替换符查找
            expression.append(src, offset, end - offset);
            break;
          }
        }
        if (end == -1) {
          // close token was not found.
          // 没找到尾巴替换符 后面串加入结果集中 包括开始替换符
          builder.append(src, start, src.length - start);
          offset = src.length;
        } else {
          // 有效替换 调用handler方法替换 对VariableTokenHandler是根据properties中name找到value替换
          builder.append(handler.handleToken(expression.toString()));
          offset = end + closeToken.length();
        }
      }
      // 继续，寻找下一个 openToken 的位置
      start = text.indexOf(openToken, offset);
    }
    // 尾巴串加入结果集
    if (offset < src.length) {
      builder.append(src, offset, src.length - offset);
    }
    return builder.toString();
  }
}
