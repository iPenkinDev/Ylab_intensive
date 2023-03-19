package io.ylab.task3.orgStructure;

import java.io.*;
import java.util.*;

public class OrgStructureParserImpl implements OrgStructureParser {
    private static final String FILE_PATH = "data.csv";
    private static final String DELIMITER = ";";

    @Override
    public Employee parseStructure(File csvFile) {
        List<Employee> employees = readEmployeesFromFile();
        buildEmployeeHierarchy(employees);
        printEmployeeHierarchy(employees);
        return employees.get(0);
    }

    private static List<Employee> readEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(OrgStructureParserImpl.FILE_PATH))) {
            String line;
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

    private static void buildEmployeeHierarchy(List<Employee> employees) {
        for (Employee employee : employees) {
            for (Employee subEmployee : employees) {
                if (employee.getId().equals(subEmployee.getBossId())) {
                    employee.getSubordinate(employee).add(subEmployee);
                    subEmployee.setBoss(employee);
                }
            }
        }
    }

    private static void printEmployeeHierarchy(List<Employee> employees) {
        for (Employee employee : employees) {
            printEmployeeHierarchy(employee, 0);
        }
    }

    private static void printEmployeeHierarchy(Employee employee, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println("- " + employee.getName() + " (" + employee.getPosition() + ")");
        for (Employee subordinate : employee.getSubordinate(employee)) {
            printEmployeeHierarchy(subordinate, level + 1);
        }

    }
}
