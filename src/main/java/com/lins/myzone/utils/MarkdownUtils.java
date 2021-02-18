package com.lins.myzoom.utils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @ClassName MarkdownUtils
 * @Description TODO
 * @Author lin
 * @Date 2021/2/7 12:49
 * @Version 1.0
 **/
public class MarkdownUtils {
    public static String markdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }
    //增加扩展【标题锚点，表格生成】
    public static String markdownToHtmlExtensions(String markdown){
        //h标题生成id
        Set<Extension> headingAnchorExtensions= Collections.singleton(HeadingAnchorExtension.create());
        //转换table的html
        List<Extension> tableExtensions= Arrays.asList(TablesExtension.create());
        Parser parser=Parser.builder()
                .extensions(tableExtensions)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtensions)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                }).build();
        return renderer.render(document);
    }
    //处理标签的属性
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            //改变a标签的target属性为_blank
            if(node instanceof List){
                map.put("target","_blank");
            }
            if(node instanceof TableBlock){
                map.put("class","ui celled table");
            }
        }
    }
}
