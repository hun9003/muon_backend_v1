package com.muesli.music.track;

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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class TrackApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("track/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("트랙 조회 TEST")
    public void retrieveTrack() throws Exception {
        this.mockMvc.perform(get("/api/v1/track/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("track/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("트랙 IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("트랙 순서"),
                                fieldWithPath("data.duration").type(JsonFieldType.NUMBER).description("트랙 길이"),
                                fieldWithPath("data.musicUrl").type(JsonFieldType.STRING).description("트랙 경로").optional(),
                                fieldWithPath("data.composer").type(JsonFieldType.STRING).description("작곡가").optional(),
                                fieldWithPath("data.lyricser").type(JsonFieldType.STRING).description("작사가").optional(),
                                fieldWithPath("data.arranger").type(JsonFieldType.STRING).description("편곡가").optional(),
                                fieldWithPath("data.adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.lyricsInfo.id").type(JsonFieldType.NUMBER).description("가사 IDX").ignored(),
                                fieldWithPath("data.lyricsInfo.lyrics").type(JsonFieldType.STRING).description("가사 정보").ignored(),
                                fieldWithPath("data.artistInfo.id").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.artistInfo.name").type(JsonFieldType.STRING).description("아티스트 이름").optional(),
                                fieldWithPath("data.artistInfo.originalName").type(JsonFieldType.STRING).description("아티스트 이름(원어)").optional(),
                                fieldWithPath("data.artistInfo.englishName").type(JsonFieldType.STRING).description("아티스트 이름(영어)").optional(),
                                fieldWithPath("data.albumInfo.id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.albumInfo.name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.albumInfo.image").type(JsonFieldType.STRING).description("앨범 이미지").optional()
                        )
                ));
    }

    @Test
    @DisplayName("좋아하는 트랙 조회 TEST")
    public void retrieveLikeTrackList() throws Exception {
        this.mockMvc.perform(get("/api/v1/track/likeables")
                        .header("Authorization", JWT_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("track/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("리스트 타입"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("곡 순위 조회 TEST")
    public void retrieveTrackRank() throws Exception {
        this.mockMvc.perform(get("/api/v1/track/rank/{type}", "month")
                        .param("date", "2022-06-01")
                        .param("genre", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("track/{method-name}",
                        pathParameters(
                                parameterWithName("type").description("날짜 타입")
                        ),
                        requestParameters(
                                parameterWithName("date").description("기준 날짜"),
                                parameterWithName("genre").description("장르 IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.date").type(JsonFieldType.STRING).description("기준 날짜"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("리스트 타입"),
                                fieldWithPath("data.genre").type(JsonFieldType.NUMBER).description("장르 IDX"),
                                fieldWithPath("data.layout.genreList[].id").type(JsonFieldType.NUMBER).description("장르 IDX").optional(),
                                fieldWithPath("data.layout.genreList[].name").type(JsonFieldType.STRING).description("장르 이름").optional(),
                                fieldWithPath("data.layout.genreList[].image").type(JsonFieldType.STRING).description("장르 이미지").optional(),
                                fieldWithPath("data.layout.genreList[].displayName").type(JsonFieldType.STRING).description("장르 노출 이름").optional(),
                                fieldWithPath("data.layout.genreList[].title").type(JsonFieldType.STRING).ignored(),
                                fieldWithPath("data.layout.genreList[].description").type(JsonFieldType.STRING).ignored(),
                                fieldWithPath("data.layout.genreList[].views").type(JsonFieldType.NUMBER).description("장르 조회수"),
                                fieldWithPath("data.layout.genreList[].albumList").type(JsonFieldType.ARRAY).ignored(),
                                fieldWithPath("data.layout.dateList[]").type(JsonFieldType.ARRAY).description("날짜 레이아웃").optional(),
                                fieldWithPath("data.trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.trackList[].rank").type(JsonFieldType.NUMBER).description("트랙 순위"),
                                fieldWithPath("data.trackList[].wave").type(JsonFieldType.NUMBER).description("트랙 순위 변동량").optional(),
                                fieldWithPath("data.trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("최신 트랙 조회 TEST")
    public void retrieveNewTrack() throws Exception {
        this.mockMvc.perform(get("/api/v1/track/new")
                        .param("size", "50")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("track/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("표시 개수"),
                                parameterWithName("page").description("페이지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("리스트 타입"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("최근 들은 트랙 조회 TEST")
    public void retrieveUserHistoryTrack() throws Exception {
        this.mockMvc.perform(get("/api/v1/track/history")
                        .header("Authorization", JWT_TOKEN)
                        .param("size", "50")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("track/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("표시 개수"),
                                parameterWithName("page").description("페이지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("리스트 타입"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

}
