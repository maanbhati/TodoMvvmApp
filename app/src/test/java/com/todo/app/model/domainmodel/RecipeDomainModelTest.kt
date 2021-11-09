package com.todo.app.model.domainmodel

import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipeDomainModelTest {
    private lateinit var domainModel: RecipeDomainModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        domainModel = RecipeDomainModel(
            0, listOf("Test"), listOf("Test"),
            "Image", "Label", "Share", "Source",
            "Url", "Details"
        )
    }

    @Test
    fun verify_domain_model_data() {
        assert(domainModel.id == 0)
        assert(domainModel.cuisineType == listOf("Test"))
        assert(domainModel.dishType == listOf("Test"))
        assert(domainModel.image == "Image")
        assert(domainModel.label == "Label")
        assert(domainModel.shareAs == "Share")
        assert(domainModel.source == "Source")
        assert(domainModel.url == "Url")
        assert(domainModel.recipeDetail == "Details")
    }
}