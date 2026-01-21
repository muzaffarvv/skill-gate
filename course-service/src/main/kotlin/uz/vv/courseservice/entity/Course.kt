package uz.vv.courseservice.entity

import jakarta.persistence.*
import uz.vv.courseservice.base.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "courses")
class Course(
    @Column(nullable = false, length = 72, unique = true)
    var title: String? = null,

    @Column(length = 120)
    var description: String? = null,

    @Column(nullable = false)
    var price: BigDecimal = BigDecimal.ZERO,

    var soldCount: Int = 0,

    @ElementCollection
    @CollectionTable(name = "course_students", joinColumns = [JoinColumn(name = "course_id")])
    @Column(name = "student_id")
    var studentsIds: MutableList<Long> = mutableListOf(),

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true)
    var lessons: MutableList<Lesson> = mutableListOf()
) : BaseEntity()