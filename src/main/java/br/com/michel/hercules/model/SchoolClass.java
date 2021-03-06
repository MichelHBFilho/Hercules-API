package br.com.michel.hercules.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "schoolclasses")
public class SchoolClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer classNumber;
	private Integer year;
	private String room;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolClass")
	private List<Student> students = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Employee> teachers = new ArrayList<>();

	public SchoolClass() {
	}

	public BigDecimal getAverage() {
		List<Grade> grades = students.stream().map(s -> s.getGrades()).flatMap(List::stream).toList();
		BigDecimal sum = BigDecimal.ZERO;
		for(Grade grade : grades) 
			sum = sum.add(grade.getValue());
		return sum.divide(new BigDecimal(grades.size()));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Employee> getTeachers() {
		return teachers;
	}

	public void addTeacher(Employee teacher) {
		this.teachers.add(teacher);
	}
	
	public void deleteTeacher(Employee teacher) {
		this.teachers.remove(teacher);
	}
	
	public void setTeachers(List<Employee> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "SchoolClass  {\n\tid=" + id + ", \n\tclassNumber=" + classNumber + ", \n\tyear=" + year + ", \n\troom="
				+ room + ", \n\tstudents=" + students + ", \n\tteachers=" + teachers + "\n}";
	}
	
	

}
