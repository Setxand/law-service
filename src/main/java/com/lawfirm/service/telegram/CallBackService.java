package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.CallBackQuery;
import com.lawfirm.utils.TextUtils;
import org.springframework.stereotype.Service;

@Service
public class CallBackService {

	private final MetaService metaService;

	public CallBackService(MetaService metaService) {
		this.metaService = metaService;
	}

	public void parseCallBack(CallBackQuery callBackQuery) {

		switch (CallBackData.valueOf(TextUtils.getData(callBackQuery.getData()))) {
			case BACKGROUND_QUESTION:
				metaService.backgroundQuestion(callBackQuery);
				break;
		}

	}
}
