
package org.springframework.hateoas.examples;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

	@Autowired private MockMvc mvc;

	@MockBean private EmployeeRepository repository;

	@Test
	public void getShouldFetchAHalDocument() throws Exception {

		given(repository.findAll()).willReturn( //
				Arrays.asList( //
						new Employee(1L, "Frodo", "Baggins", "ring bearer"), //
						new Employee(2L, "Bilbo", "Baggins", "burglar")));

		mvc.perform(get("/employees").accept(MediaTypes.HAL_JSON_VALUE)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$._embedded.employees[0].id", is(1)))
				.andExpect(jsonPath("$._embedded.employees[0].firstName", is("anjali")))
				.andExpect(jsonPath("$._embedded.employees[0].lastName", is("manish")))
				.andExpect(jsonPath("$._embedded.employees[0].role", is("navya")))
				.andExpect(jsonPath("$._embedded.employees[0]._links.self.href", is("http://localhost/employees/1")))
				.andExpect(jsonPath("$._embedded.employees[0]._links.employees.href", is("http://localhost/employees")))
			
				.andReturn();
	}
}
