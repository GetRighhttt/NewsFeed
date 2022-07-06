package com.example.newsfeed.newsapi.data.api

import com.example.newsfeed.data.api.NewsApiService
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

/*
This is how we are going to test our API client to make sure that the information is accurately
working.

We use Mock Web Servers when we want to test network efficiency.

We need to create a retrofit instance, create a reader to read the information from the JSON
file we created in our resources section, and convert them into a string.

Then we add the string response to the query of the mock web server.

After that, we can write test cases for each of the methods we defined in our NewsResponse.
 */
class NewsApiServiceTest {
    private lateinit var service: NewsApiService
    private lateinit var server: MockWebServer

    /*
    Method to run before the test. Creating a Retrofit instance using the same way we always do.
     */
    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
    /*
    Method we are using to read the information from our JSON object and parse it into a string.
     */
    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    /*
    First test case to test and see if we are getting the correct response for our get method from
    our Network response.

    runBlocking{} runs a new coroutine thread and blocks the current thread until its completion.
    It's what we use for coroutines when we are unit testing.

    Truth library provides extra functions for testing which we implement in our build gradle files.
     */
    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1)
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path)
                .isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=fcf017ca90a244818b520ff16c6a8a29")
        }
    }


    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)

        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[0]
            assertThat(article.author).isEqualTo("Saheli Roy Choudhury")
            assertThat(article.url).isEqualTo("https://www.cnbc.com/2021/01/04/samsung-galaxy-unpacked-2021.html")
            assertThat(article.publishedAt).isEqualTo("2021-01-04T03:25:00Z")
        }
    }

    /*
    Shuts down the server after the testing has concluded.
    */
    @After
    fun tearDown() {
        server.shutdown()
    }
}