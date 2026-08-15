package com.example.newsfeed.presentation.model

import com.example.newsfeed.data.model.Results
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleArgsTest {

    @Test
    fun articleSurvivesNavigationRoundTrip() {
        val article = Results(
            id = 42,
            articleId = "article-id",
            author = "Author",
            title = "Title",
            link = "https://example.com/article",
            description = "Description",
            pubDate = "2026-08-15",
            image_url = "https://example.com/image.jpg"
        )

        assertEquals(article, article.toArticleArgs().toResults())
    }
}
