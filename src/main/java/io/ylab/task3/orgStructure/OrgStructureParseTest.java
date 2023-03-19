package io.ylab.task3.orgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParseTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParserImpl parser = new OrgStructureParserImpl();
        Employee employee = parser.parseStructure(new File("data.csv"));
        System.out.println(employee);
    }
}
