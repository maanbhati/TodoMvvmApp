package com.todo.app.model.domainmodel

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HitDomainModelTest {
    @MockK
    private lateinit var recipeDomainModel: RecipeDomainModel

    private lateinit var domainModel: HitDomainModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        domainModel = HitDomainModel(recipeDomainModel)
    }

    @Test
    fun verify_domain_model_data() {
        assert(domainModel.recipe == recipeDomainModel)
    }
}