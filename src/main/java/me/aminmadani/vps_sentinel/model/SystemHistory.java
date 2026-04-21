package me.aminmadani.vps_sentinel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity // ۱. به اسپرینگ می‌گه این کلاس یک جدول در دیتابیسه
@Table(name = "system_history") // ۲. (اختیاری) اسم جدول رو تعیین می‌کنه
@Data // از لومبوک برای ساخت Getter و Setter
public class SystemHistory {

    @Id // ۳. مشخص کردن کلید اصلی (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ۴. مقداردهی خودکار (مثل Auto Increment)
    private Long id;

    private Double cpuUsage;
    private Double memUsage;

    @Column(name = "recorded_at") // ۵. (اختیاری) اسم ستون در دیتابیس
    private LocalDateTime timestamp;
}