package com.muesli.music.artist;

import com.muesli.music.JwtToken;
import com.muesli.music.MuonBackendV1Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class ArtistApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("artist/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("???????????? ?????? TEST")
    public void retrieveArtist() throws Exception {
        this.mockMvc.perform(get("/api/v1/artist/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("artist/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("???????????? IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.originalName").type(JsonFieldType.STRING).description("???????????? ??????(??????)").optional(),
                                fieldWithPath("data.englishName").type(JsonFieldType.STRING).description("???????????? ??????(??????)").optional(),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                fieldWithPath("data.birthday").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.country").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.debut").type(JsonFieldType.NUMBER).description("???????????? ????????????"),
                                fieldWithPath("data.agency").type(JsonFieldType.STRING).description("???????????? ?????????").optional(),
                                fieldWithPath("data.label").type(JsonFieldType.STRING).description("???????????? ?????????").optional(),
                                fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                fieldWithPath("data.imageSmall").type(JsonFieldType.STRING).description("???????????? ?????????(?????? ?????????)").optional(),
                                fieldWithPath("data.biosInfo.id").type(JsonFieldType.NUMBER).description("???????????? ?????? IDX"),
                                fieldWithPath("data.biosInfo.content[]").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                fieldWithPath("data.albumList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.albumList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.albumList[].releaseDate").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.albumList[].originalName").type(JsonFieldType.STRING).description("?????? ??????(??????)").optional(),
                                fieldWithPath("data.albumList[].image").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.albumList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.albumList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.albumList[].trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.albumList[].trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.albumList[].trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.albumList[].trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.albumList[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.albumList[].trackList[].image").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.albumList[].trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.albumList[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.albumList[].trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.albumList[].trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.albumList[].trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.albumList[].trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.albumList[].trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.albumList[].trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.albumList[].trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("???????????? ???????????? ?????? TEST")
    public void retrieveLikeArtistList() throws Exception {
        this.mockMvc.perform(get("/api/v1/artist/likeables")
                        .header("Authorization", JWT_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("artist/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data[].originalName").type(JsonFieldType.STRING).description("???????????? ??????(??????)").optional(),
                                fieldWithPath("data[].englishName").type(JsonFieldType.STRING).description("???????????? ??????(??????)").optional(),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                fieldWithPath("data[].birthday").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data[].country").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data[].debut").type(JsonFieldType.NUMBER).description("???????????? ????????????")
                        )
                ));
    }

}
