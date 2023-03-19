package io.ylab.task3.orgStructure;

import java.io.*;
import java.util.*;

public class OrgStructureParserImpl implements OrgStructureParser {
    private static final String FILE_PATH = "data.csv";
    private static final String DELIMITER = ";";

    @Override
    public Employee parseStructure(File csvFile) {
        List<Employee> employees = readEmployeesFromFile();
        Employee boss = buildEmployeeHierarchy(employees);
        return boss;
    }

    private static List<Employee> readEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(OrgStructureParserImpl.FILE_PATH))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                Employee employee = new Employee();
                employee.setId(Long.parseLong(values[0]));
                employee.setBossId(values[1].isEmpty() ? null : Long.parseLong(values[1]));
                employee.setName(values[2]);
                employee.setPosition(values[3]);
                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static Employee buildEmployeeHierarchy(List<Employee> employees) {
        for (Employee employee : employees) {
            for (Employee subEmployee : employees) {
                if (employee.getId().equals(subEmployee.getBossId())) {
                    employee.getSubordinate(employee).add(subEmployee);
                    subEmployee.setBoss(employee);
                }
            }
        }
        Employee boss = null;
        for (Employee employee : employees) {
            if (employee.getBoss() == null) {
                boss = employee;
            }
        }
        return boss;
    }
}
