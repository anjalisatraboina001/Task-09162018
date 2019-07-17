
package org.springframework.hateoas.examples;

import org.springframework.data.repository.CrudRepository;


interface EmployeeRepository extends CrudRepository<Employee, Long> {}
