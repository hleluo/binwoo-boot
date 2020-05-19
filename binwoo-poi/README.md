### 1、Excel操作

#### 1.1、写数据

* 一次性写数据 
```
ExcelWriter writer = new ExcelWriter();
// 定义样式
ExcelCellDecorator decorator = new ExcelCellDecorator() {
  @Override
  public void decorate(CellStyle style, Font font) {
    // 设置粗体
    font.setBold(true);
  }
};
List<ExcelRow> rows = new ArrayList<>();
// 添加表头
ExcelRow header = new ExcelRow(new ExcelCell("姓名").decorate(decorator),
    new ExcelCell("年龄").decorate(decorator),
    new ExcelCell("金额").decorate(decorator));
rows.add(header);
// 添加行数据
for (int i = 0; i < 2; i++) {
  ExcelRow row = new ExcelRow(new ExcelCell("张三"),
      new ExcelCell(i + 20, Integer.class),
      new ExcelCell(123456, Integer.class, "#,##0.00"));
  rows.add(row);
}
// 写入文件
writer.write("demo", rows, null, "D:\\demo.xlsx");
```

| 姓名|	年龄 |	金额|
| :---: | :---:  | :---:  |
|张三 |	20 |	123,456.00|
|张三 |	21 |	123,456.00| 

* 循环写数据
```
writer.write("demo", new ExcelRowLoader() {
  @Override
  public void load(ExcelWriter writer, Workbook workbook, Sheet sheet) {
    for (int i = 0; i < 2; i++) {
      ExcelRow row = new ExcelRow(new ExcelCell("张三"),
        new ExcelCell(i + 20, Integer.class),
        new ExcelCell(123456, Integer.class, "#,##0.00"));
      writer.append(sheet, i + 1, row);
    }
  }
}, null, "D:\\demo.xlsx");
```
* 大数据量
```
writer.setMassive(true);
```
* 表格合并
```
List<ExcelMerge> merges = Arrays.asList(new ExcelMerge(0,0,0,2),
          new ExcelMerge(0,0,5,8));
```

#### 1.2、读数据 

| 姓名|	年龄 |	金额| tag |
| :---: | :---:  | :---:  |:---:  |
|张三 |	20 |	123,456.00| 文艺青年 |
|张三 |	21 |	123,456.00| 文学家 |
```
ExcelReader reader = new ExcelReader("D:\\demo.xlsx");
CellParser<User> parser = new CellParser<User>() {
  @Override
  public void parse(User object, int index, String key, Object value, Cell cell) {
    // 根据索引获取值
    switch (index) {
      case 0:
        object.setName((String) value);
        break;
      case 1:
        object.setAge(Integer.parseInt((String) value));
        break;
      case 2:
        object.setMoney(Double.parseDouble((String) value));
        break;
    }
    // 根据key获取值，对应的需要指定keyIndex所在的行
    if ("tag".equals(key)) {
      object.setTag((String) value);
    }
  }
};
List<User> users = reader.readByIndex(null, 1, null, User.class, 0, parser);
// 关闭文件流
reader.close();
```
### 2、PDF操作
#### 2.1、Word转PDF
```
PdfUtils.fromWord("D:\\test\\export.docx", "D:\\test\\docx_pdf.pdf");
```
#### 2.2、PDF添加图片
```
PdfHelper render = new PdfHelper("D:\\test\\docx_pdf.pdf");
PdfPicture picture = new PdfPicture();
picture.setLeft(10);
picture.setTop(10);
picture.setFilepath("D:\\test\\440300000000.png");
picture.setOriginal(true);
render.addPicture(1, picture, "D:\\test\\pdf_picture.pdf");
render.close();
```
### 3、Word操作
#### 3.1、Word模板渲染
* 参数标识：
```
${param_name}
```
* 表格参数： 

|ROW_REPEAT#users||||
|---|---|---|---|
|{name}|{sex}|{nickname}|{project}|
```
private static Map<String, Object> buildUser() {
  Map<String, Object> user = new HashMap<>();
  user.put("name", "张三");
  user.put("sex", "男");
  user.put("nickname", "图图");
  user.put("project", buildPicture());
  return user;
}

private static WordPicture buildPicture() {
  WordPicture picture = new WordPicture();
  picture.setWidth(200);
  picture.setHeight(200);
  picture.setFilename("440300000000.png");
  try (InputStream input = new FileInputStream("D:\\test\\440300000000.png")) {
    byte[] content = new byte[input.available()];
    int length = input.read(content);
    picture.setContent(content);
  } catch (IOException e) {
    e.printStackTrace();
  }
  return picture;
}

Map<String, Object> params = new HashMap<>();
params.put("fileNumber_Text", buildPicture());
params.put("test-a", "测试1");
params.put("testBen", "测试2");
params.put("ten", "测试测试");
WordRow wordRow = new WordRow();
wordRow.add(buildUser());
wordRow.add(buildUser());
WordRender render = new WordRender("D:\\test\\template.docx");
params.put("users", wordRow);
render.build("D:\\test\\export.docx", params);
render.close();
```
#### 3.2、Word工具类
* HTML转Word
```
WordUtils.toHtml("D:\\test\\export.docx", "D:\\test\\docx_html.html");
```
* Word互转
```
WordUtils.docToDocx("D:\\test\\export.doc", "D:\\test\\doc_docx.docx");
```
### 4、其他
* Excel操作：https://github.com/alibaba/easyexcel
```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>2.2.3</version>
</dependency>
```
* Word模板引擎：http://deepoove.com/poi-tl/
```
<dependency>
  <groupId>com.deepoove</groupId>
  <artifactId>poi-tl</artifactId>
  <version>1.7.3</version>
</dependency>
```


