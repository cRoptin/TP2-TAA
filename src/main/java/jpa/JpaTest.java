package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import domain.Department;
import domain.Employee;

public class JpaTest {
	
	private EntityManager manager;

	private Department createNewDepartment() {
		Department dep = new Department();
		dep.setName("RD");
		manager.persist(dep);
		return dep;
	}

	private void createNewEmployee() {
		List<Employee> loExistEmp = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
		if (loExistEmp != null && loExistEmp.size() > 0) {
			for (Employee oEmp : loExistEmp) {
				manager.persist(oEmp);
			}
		}
		List<Department> loExistDep = manager.createQuery("Select a From Department a", Department.class).getResultList();
		if (loExistDep != null && loExistDep.size() > 0) {
			for (Department oDep : loExistDep) {
				manager.persist(oDep);
			}
		}
		Employee emp = new Employee();
		emp.setName("Dupont");
		emp.setDepartment(createNewDepartment());
		manager.persist(emp);
	}
	
	public JpaTest(EntityManager manager) {
        this.manager = manager;
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			// TODO
			test.createNewEmployee();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		manager.close();
		factory.close();
	}

}
