package io.ylab.task5.lesson05.eventsourcing;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Person {
  private Long id;
  private String name;
  private String lastName;
  private String middleName;

  public Person() {
  }

  public Person(Long id, String name, String lastName, String middleName) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.middleName = middleName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (!Objects.equals(id, person.id)) return false;
    if (!Objects.equals(name, person.name)) return false;
    if (!Objects.equals(lastName, person.lastName)) return false;
    return Objects.equals(middleName, person.middleName);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", middleName='" + middleName + '\'' +
            '}';
  }
}
