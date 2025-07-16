"use client";

import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50 px-4">
      <div className="max-w-3xl text-center">
        <h1 className="text-xl md:text-5xl font-bold mb-6 text-gray-800">
          안녕하세요, <span className="text-blue-600">Grids & Circles</span>{" "}입니다.
        </h1>

        <p className="text-lg text-gray-700 mb-4">
          매일 오후 <span className="font-semibold">2시</span>부터 다음 날 오후 <span className="font-semibold">2시</span>까지의 주문을 모아서 처리합니다.
        </p>

        <p className="text-md text-gray-500 mb-8">
          온라인으로 신선한 음료와 디저트를 간편하게 예약하세요.
        </p>

        <Link
          href="/products"
          className="inline-block bg-blue-600 text-white px-6 py-3 rounded-full text-lg font-semibold hover:bg-blue-700 transition"
        >
          주문하러 가기
        </Link>
      </div>
    </div>
  );
}