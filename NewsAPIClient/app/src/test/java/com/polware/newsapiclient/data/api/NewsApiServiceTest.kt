package com.polware.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var service: NewsApiService
    private lateinit var server: MockWebServer
    private val apiKey = "bc4caee230db45218cee7be3d1ca098b"

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(filename: String) {
        val inputStream = javaClass.classLoader.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getNewsTopHeadlines_sentRequest_receiveExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getNewsTopHeadlines("us", 1, apiKey).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/top-headlines?country=us&page=1&apiKey=bc4caee230db45218cee7be3d1ca098b")
        }
    }

    @Test
    fun getTopHeadlines_receiveResponse_correctPageResults() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getNewsTopHeadlines("us", 1, apiKey).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receiveResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getNewsTopHeadlines("us", 1, apiKey).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[0]
            assertThat(article.author).isEqualTo("Kerry Breen")
            assertThat(article.url).isEqualTo("https://www.cbsnews.com/news/lisa-marie-presley-cardiac-arrest/")
        }
    }

}