package com.binwoo.poi.word;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

/**
 * 扩展Document.
 *
 * @author admin
 * @date 2019/9/23 17:28
 */
public class WordDocument extends XWPFDocument {

  public WordDocument(InputStream in) throws IOException {
    super(in);
  }

  public WordDocument() {
    super();
  }

  public WordDocument(OPCPackage pkg) throws IOException {
    super(pkg);
  }

  /**
   * 构建图片.
   *
   * @param width 宽
   * @param height 高
   * @param paragraph 段落
   */
  public void buildPicture(int id, int width, int height, XWPFParagraph paragraph) {
    final int emu = 9525;
    width *= emu;
    height *= emu;
    String blipId = getAllPictures().get(id).getPackageRelationship().getId();
    CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
    String picXml = ""
        + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
        + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
        + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
        + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
        + id
        + "\" name=\"Generated\"/>"
        + "            <pic:cNvPicPr/>"
        + "         </pic:nvPicPr>"
        + "         <pic:blipFill>"
        + "            <a:blip r:embed=\""
        + blipId
        + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
        + "            <a:stretch>"
        + "               <a:fillRect/>"
        + "            </a:stretch>"
        + "         </pic:blipFill>"
        + "         <pic:spPr>"
        + "            <a:xfrm>"
        + "               <a:off x=\"0\" y=\"0\"/>"
        + "               <a:ext cx=\""
        + width
        + "\" cy=\""
        + height
        + "\"/>"
        + "            </a:xfrm>"
        + "            <a:prstGeom prst=\"rect\">"
        + "               <a:avLst/>"
        + "            </a:prstGeom>"
        + "         </pic:spPr>"
        + "      </pic:pic>"
        + "   </a:graphicData>" + "</a:graphic>";

    inline.addNewGraphic().addNewGraphicData();
    XmlToken xmlToken = null;
    try {
      xmlToken = XmlToken.Factory.parse(picXml);
    } catch (XmlException xe) {
      xe.printStackTrace();
    }
    inline.set(xmlToken);

    inline.setDistT(0);
    inline.setDistB(0);
    inline.setDistL(0);
    inline.setDistR(0);

    CTPositiveSize2D size = inline.addNewExtent();
    size.setCx(width);
    size.setCy(height);

    CTNonVisualDrawingProps props = inline.addNewDocPr();
    props.setId(id);
    props.setName("pic-" + id);
    props.setDescr(props.getName());
  }

}
