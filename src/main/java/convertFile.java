/**
 * Copyright (C), 2018-2018, 河北东软有限公司
 * FileName: convertFile
 * Author:   fqq
 * Date:     2018/6/22 11:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.asciidoctor.*;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.Placement;
import org.asciidoctor.SafeMode;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author fqq
 * @create 2018/6/22
 * @since 1.0.0
 */
public class convertFile {
    public static void main(String[] args) throws  Exception{
        //  输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();
        //swagger地址
        String url="http://172.30.3.217:6301";
        //导入文件名
        String fileName="会议室预定";
        String adocPath = "./asciidoc/"+fileName;
        Swagger2MarkupConverter.from(new URL(url+"/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get(adocPath));
        //生成pdf文档
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        Attributes attributes = new Attributes();
        attributes.setCopyCss(true);
        attributes.setLinkCss(false);
        attributes.setSectNumLevels(3);
        attributes.setAnchors(true);
        attributes.setSectionNumbers(true);
        attributes.setHardbreaks(false);
        attributes.setTableOfContents(Placement.LEFT);
        attributes.setAttribute("toclevels","2");
        attributes.setAttribute("pdf-fontsdir","./src/main/resources/fonts");
        attributes.setAttribute("pdf-style","./src/main/resources/theme.yml");
        OptionsBuilder optionsBuilder = OptionsBuilder.options()
                .backend("pdf")
//                .backend("html")
                .docType("book")
                .eruby("")
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .attributes(attributes);
        String asciiInputFile =adocPath+".adoc";
        asciidoctor.convertFile(
                new File(asciiInputFile),
                optionsBuilder.get());
    }

}