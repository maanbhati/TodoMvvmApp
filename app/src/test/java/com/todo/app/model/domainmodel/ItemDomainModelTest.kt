package com.todo.app.model.domainmodel

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemDomainModelTest {
    @MockK
    private lateinit var hitDomainModel: HitDomainModel

    private lateinit var domainModel: ItemDomainModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        domainModel = ItemDomainModel(listOf(hitDomainModel))
    }

    @Test
    fun verify_domain_model_data() {
        assert(domainModel.hits == listOf(hitDomainModel))
    }
}