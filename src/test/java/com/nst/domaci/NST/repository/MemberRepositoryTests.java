package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
@Disabled
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AcademicTitleRepository academicTitleRepository;

    @Autowired
    private EducationTitleRepository educationTitleRepository;

    @Autowired
    private ScientificFieldRepository scientificFieldRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        departmentRepository.deleteAll();
    }

    @Test
    public void findAllByDepartmentIdTest() {
        AcademicTitle academicTitle = new AcademicTitle();
        academicTitle.setName("Some Academic Title");
        academicTitleRepository.save(academicTitle);

        EducationTitle educationTitle = new EducationTitle();
        educationTitle.setName("Some Education Title");
        educationTitleRepository.save(educationTitle);

        ScientificField scientificField = new ScientificField();
        scientificField.setName("Some Scientific Field");
        scientificFieldRepository.save(scientificField);

        Department department = new Department();
        department.setName("Department A");
        department.setShortName("Dept A");
        department.setMembers(new ArrayList<>());
        departmentRepository.save(department);

        Member member1 = new Member();
        member1.setFirstName("John");
        member1.setLastName("Doe");
        member1.setDepartment(department);
        member1.setAcademicTitle(academicTitle);
        member1.setEducationTitle(educationTitle);
        member1.setScientificField(scientificField);
        department.getMembers().add(member1);

        Member member2 = new Member();
        member2.setFirstName("Jane");
        member2.setLastName("Doe");
        member2.setDepartment(department);
        member2.setAcademicTitle(academicTitle);
        member2.setEducationTitle(educationTitle);
        member2.setScientificField(scientificField);
        department.getMembers().add(member2);

        memberRepository.save(member1);
        memberRepository.save(member2);


        List<Member> members = memberRepository.findAllByDepartmentId(department.getId());

        Assertions.assertEquals(2, members.size());
        Assertions.assertTrue(members.contains(member1));
        Assertions.assertTrue(members.contains(member2));
    }

    @Test
    public void findAllByEducationTitleIdTest() {
        AcademicTitle academicTitle = new AcademicTitle();
        academicTitle.setName("Some Academic Title");
        academicTitleRepository.save(academicTitle);

        EducationTitle educationTitle = new EducationTitle();
        educationTitle.setName("Some Education Title");
        educationTitleRepository.save(educationTitle);

        ScientificField scientificField = new ScientificField();
        scientificField.setName("Some Scientific Field");
        scientificFieldRepository.save(scientificField);

        Department department = new Department();
        department.setName("Department A");
        department.setShortName("Dept A");
        department.setMembers(new ArrayList<>());
        departmentRepository.save(department);

        Member member1 = new Member();
        member1.setFirstName("John");
        member1.setLastName("Doe");
        member1.setDepartment(department);
        member1.setAcademicTitle(academicTitle);
        member1.setEducationTitle(educationTitle);
        member1.setScientificField(scientificField);
        department.getMembers().add(member1);

        Member member2 = new Member();
        member2.setFirstName("Jane");
        member2.setLastName("Doe");
        member2.setDepartment(department);
        member2.setAcademicTitle(academicTitle);
        member2.setEducationTitle(educationTitle);
        member2.setScientificField(scientificField);
        department.getMembers().add(member2);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAllByEducationTitleId(educationTitle.getId());

        Assertions.assertEquals(2, members.size());
        Assertions.assertTrue(members.contains(member1));
        Assertions.assertTrue(members.contains(member2));
    }

    @Test
    public void findAllByAcademicTitleIdTest() {
        AcademicTitle academicTitle = new AcademicTitle();
        academicTitle.setName("Some Academic Title");
        academicTitleRepository.save(academicTitle);

        EducationTitle educationTitle = new EducationTitle();
        educationTitle.setName("Some Education Title");
        educationTitleRepository.save(educationTitle);

        ScientificField scientificField = new ScientificField();
        scientificField.setName("Some Scientific Field");
        scientificFieldRepository.save(scientificField);

        Department department = new Department();
        department.setName("Department A");
        department.setShortName("Dept A");
        department.setMembers(new ArrayList<>());
        departmentRepository.save(department);

        Member member1 = new Member();
        member1.setFirstName("John");
        member1.setLastName("Doe");
        member1.setDepartment(department);
        member1.setAcademicTitle(academicTitle);
        member1.setEducationTitle(educationTitle);
        member1.setScientificField(scientificField);
        department.getMembers().add(member1);

        Member member2 = new Member();
        member2.setFirstName("Jane");
        member2.setLastName("Doe");
        member2.setDepartment(department);
        member2.setAcademicTitle(academicTitle);
        member2.setEducationTitle(educationTitle);
        member2.setScientificField(scientificField);
        department.getMembers().add(member2);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAllByAcademicTitleId(academicTitle.getId());

        Assertions.assertEquals(2, members.size());
        Assertions.assertTrue(members.contains(member1));
        Assertions.assertTrue(members.contains(member2));
    }

    @Test
    public void findAllByScientificFieldIdTest() {
        AcademicTitle academicTitle = new AcademicTitle();
        academicTitle.setName("Some Academic Title");
        academicTitleRepository.save(academicTitle);

        EducationTitle educationTitle = new EducationTitle();
        educationTitle.setName("Some Education Title");
        educationTitleRepository.save(educationTitle);

        ScientificField scientificField = new ScientificField();
        scientificField.setName("Some Scientific Field");
        scientificFieldRepository.save(scientificField);

        Department department = new Department();
        department.setName("Department A");
        department.setShortName("Dept A");
        department.setMembers(new ArrayList<>());
        departmentRepository.save(department);

        Member member1 = new Member();
        member1.setFirstName("John");
        member1.setLastName("Doe");
        member1.setDepartment(department);
        member1.setAcademicTitle(academicTitle);
        member1.setEducationTitle(educationTitle);
        member1.setScientificField(scientificField);
        department.getMembers().add(member1);

        Member member2 = new Member();
        member2.setFirstName("Jane");
        member2.setLastName("Doe");
        member2.setDepartment(department);
        member2.setAcademicTitle(academicTitle);
        member2.setEducationTitle(educationTitle);
        member2.setScientificField(scientificField);
        department.getMembers().add(member2);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAllByScientificFieldId(scientificField.getId());

        Assertions.assertEquals(2, members.size());
        Assertions.assertTrue(members.contains(member1));
        Assertions.assertTrue(members.contains(member2));
    }


}
