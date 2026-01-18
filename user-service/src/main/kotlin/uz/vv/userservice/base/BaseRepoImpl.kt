package uz.vv.userservice.base

import jakarta.persistence.EntityManager
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional

class BaseRepoImpl<T : BaseEntity>(
    entityInformation: JpaEntityInformation<T, Long>,
    private val entityManager: EntityManager
) : SimpleJpaRepository<T, Long>(entityInformation, entityManager), BaseRepo<T> {

    private val notDeleted = Specification<T> { root, _, cb ->
        cb.equal(root.get<Boolean>("deleted"), false)
    }

    override fun findByIdAndDeletedFalse(id: Long): T? =
        findOne(notDeleted.and { root, _, cb -> cb.equal(root.get<Long>("id"), id) }).orElse(null)

    @Transactional
    override fun trash(id: Long): T? = findById(id).orElse(null)?.apply {
        deleted = true
        save(this)
    }

    override fun findAllNotDeleted(): List<T> = findAll(notDeleted)

    override fun findAllNotDeleted(pageable: Pageable) = findAll(notDeleted, pageable)

    @Transactional
    override fun saveAndRefresh(t: T): T {
        val saved = save(t)
        entityManager.flush()
        entityManager.refresh(saved)
        return saved
    }
}