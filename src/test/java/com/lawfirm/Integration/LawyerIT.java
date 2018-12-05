package com.lawfirm.Integration;

import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.reposiory.ServiceBodyRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LawyerIT extends BaseIT {

	@Autowired ServiceBodyRepo serviceBodyRepo;

	@Test
	public void lawyerIT() throws Exception {

		// Get main page
		mockMvc.perform(get("/lawfirm")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Get service block
		ServiceBody serviceBody = new ServiceBody();
		serviceBody.setTitle("title-test");
		serviceBody.setId(1223L);
		serviceBody = serviceBodyRepo.saveAndFlush(serviceBody);

		mockMvc.perform(get("/lawfirm/users/{titleId}/service-bodies", serviceBody.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		serviceBodyRepo.delete(serviceBody);
	}

}
