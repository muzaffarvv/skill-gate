package uz.vv.courseservice.entity

import jakarta.persistence.*
import uz.vv.courseservice.base.BaseEntity
import uz.vv.courseservice.enum.LessonType

@Entity
@Table(name = "lessons")
class Lesson(
    @Column(nullable = false, length = 120)
    var topic: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    var contentType: LessonType = LessonType.VIDEO,

    @Column(nullable = false)
    var contentUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    var course: Course? = null
) : BaseEntity()