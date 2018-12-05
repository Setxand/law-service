package com.lawfirm.service.lawfirm;

import com.lawfirm.dto.ServiceBodyDTO;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.model.lawProject.ServiceTitle;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.ServiceBodyRepo;
import com.lawfirm.reposiory.ServiceTitleRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.lawfirm.reposiory.EditableComponents.BACKGROUND_IMAGE;
import static com.lawfirm.reposiory.EditableComponents.TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LawControllerHelperServiceTest {

	@Mock private ServiceTitleRepo serviceTitleRepo;
	@Mock private EditableComponentRepository editableComponentRepo;
	@Mock private ServiceBodyRepo serviceBodyRepo;

	@InjectMocks private LawControllerHelperService helperService;

	@Test
	public void helpParseModelForIndexTest() {
		List<ServiceTitle> list = Collections.singletonList(new ServiceTitle());

		when(serviceTitleRepo.findAll()).thenReturn(list);
		when(editableComponentRepo.findByComponentKey(TITLE)).thenReturn(Optional.of(
				new EditableComponent(TITLE, "empty title!")));

		String imageTest = "image-test";
		when(editableComponentRepo.findByComponentKey(BACKGROUND_IMAGE))
				.thenReturn(Optional.of(new EditableComponent(BACKGROUND_IMAGE, imageTest)));

		RedirectAttributesModelMap model = new RedirectAttributesModelMap();

		helperService.helpParseModelForIndex(model);

		assertEquals("empty title!", model.get("titleText"));
		assertEquals(imageTest, model.get(BACKGROUND_IMAGE.name()));

		RedirectAttributesModelMap modelTest = new RedirectAttributesModelMap();
		modelTest.addAttribute("services", list);
		assertEquals(model.get("services"), model.get("services"));
	}

	@Test
	public void helpLoadServiceBodyTest() {
		String titleId = "232323";
		ServiceBody serviceBody = createServiceBody();
		when(serviceBodyRepo.findById(Long.parseLong(titleId))).thenReturn(Optional.of(serviceBody));

		ServiceBodyDTO serviceBodyTestResult = helperService.helpLoadServiceBody(titleId);

		assertEquals(serviceBody.getBody(), serviceBodyTestResult.body);
		assertEquals(serviceBody.getTitle(), serviceBodyTestResult.title);
		assertEquals(serviceBody.getImage(), serviceBodyTestResult.image);
	}


	public static ServiceBody createServiceBody() {
		ServiceBody body = new ServiceBody();
		body.setBody("bodyTest");
		body.setTitle("titleTest");
		body.setImage("imageTest");
		return body;
	}
}