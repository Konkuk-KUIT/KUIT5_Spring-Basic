package kuit.springbasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig {

    @Bean
    public MappingJackson2JsonView jsonView() {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true); // 이거 안하면 json이라는 키값에 매핑된 객체로 한 레벨 더 들어가서 파싱을 못함
        return view;
    }
}