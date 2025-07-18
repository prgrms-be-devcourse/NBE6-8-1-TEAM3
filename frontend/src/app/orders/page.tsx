"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

// 상품, 주문자 정보 타입 정의
type Product = {
  id: number;
  productName: string;
  productPrice: number;
};

type Orders = {
  email: string;
  address: string;
  zipCode: string;
};

// 주문목록(왼쪽) 컴포넌트
function OrderList({
  product,
  quantity,
  totalPrice,
}: {
  product: Product;
  quantity: number;
  totalPrice: number;
}) {
  return (
    <div className="flex flex-col justify-between h-full">
      <div className="text-2xl font-bold mb-8">주문목록</div>
      <div className="text-xl mb-8">
        {product.productName} &nbsp; x {quantity}
      </div>
      <div className="text-xl font-semibold">
        총 금액 &nbsp; {totalPrice.toLocaleString()} 원
      </div>
    </div>
  );
}

// 주문자 정보(오른쪽) 컴포넌트 (input)
function OrdererInfo({
  orders,
  setOrders,
}: {
  orders: Orders;
  setOrders: (o: Orders) => void;
}) {
  return (
    <div className="flex flex-col justify-between text-lg h-full">
      <div className="mb-6">
        이메일
        <br />
        <input
          className="font-normal border rounded p-1 w-56"
          type="email"
          value={orders.email}
          onChange={(e) => setOrders({ ...orders, email: e.target.value })}
        />
      </div>
      <div className="mb-6">
        주소
        <br />
        <input
          className="font-normal border rounded p-1 w-56"
          type="text"
          value={orders.address}
          onChange={(e) => setOrders({ ...orders, address: e.target.value })}
        />
      </div>
      <div>
        우편주소
        <br />
        <input
          className="font-normal border rounded p-1 w-56"
          type="text"
          value={orders.zipCode}
          onChange={(e) => setOrders({ ...orders, zipCode: e.target.value })}
        />
      </div>
    </div>
  );
}

// 버튼 영역 컴포넌트
function OrderButtons({
  onModify,
  onCancel,
  onOrder,
}: {
  onModify: () => void;
  onCancel: () => void;
  onOrder: () => void;
}) {
  return (
    <div className="w-full flex flex-row justify-between gap-8 mt-4">
      <button
        className="bg-gray-100 py-8 rounded-lg text-2xl font-semibold w-1/3"
        onClick={onModify}
      >
        수정
      </button>
      <button
        className="bg-gray-100 py-8 rounded-lg text-2xl font-semibold w-1/3"
        onClick={onCancel}
      >
        취소
      </button>
      <button
        className="bg-gray-100 py-8 rounded-lg text-2xl font-semibold w-1/3"
        onClick={onOrder}
      >
        결제
      </button>
    </div>
  );
}

const NEXT_PUBLIC_API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

export default function OrderPage() {
  const router = useRouter();
  // 예시 데이터
  const [product] = useState<Product>({
    id: 1,
    productName: "상품1",
    productPrice: 5000,
  });
  const [quantity] = useState(2);
  const [orders, setOrders] = useState<Orders>({
    email: "",
    address: "",
    zipCode: "",
  });
  const totalPrice = product.productPrice * quantity;

  // localStorage에서 회원정보 불러오기
  useEffect(() => {
    const saved = localStorage.getItem("ordersInfo");
    if (saved) setOrders(JSON.parse(saved));
  }, []);

  // 수정 버튼: 입력값 저장 후 /products로 이동
  const handleModify = () => {
    localStorage.setItem("ordersInfo", JSON.stringify(orders));
    router.push("/products");
  };
  // 취소 버튼: 입력값 초기화 후 /products로 이동
  const handleCancel = () => {
    localStorage.removeItem("ordersInfo");
    setOrders({ email: "", address: "", zipCode: "" });
    router.push("/products");
  };
  // 결제 버튼: 주문정보 서버로 전송
  const handleOrder = async () => {
    try {
      const res = await fetch(`${NEXT_PUBLIC_API_BASE_URL}/api/v1/orders`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email: orders.email,
          address: orders.address,
          zipCode: orders.zipCode,
          productId: product.id,
          quantity,
          totalPrice,
        }),
      });
      if (!res.ok) throw new Error("주문 실패");
      alert("주문이 완료되었습니다!");
      localStorage.removeItem("ordersInfo");
      router.push("/products");
    } catch (e) {
      alert("주문 실패: " + e);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center bg-gray-100 py-8">
      <h1 className="text-4xl font-bold text-center mb-8">Grids & Circles</h1>
      <div className="w-full max-w-4xl bg-gray-400 p-12 rounded-lg flex flex-col items-center">
        {/* 주문 정보 박스 */}
        <div
          className="w-full bg-gray-50 p-10 rounded-lg flex flex-row justify-between mb-16"
          style={{ minHeight: 260 }}
        >
          <OrderList
            product={product}
            quantity={quantity}
            totalPrice={totalPrice}
          />
          {/* 주문자 정보(오른쪽) - 예시 텍스트로 고정 */}
          <div className="flex flex-col justify-between text-lg h-full">
            <div className="mb-6">
              이메일
              <br />
              <span className="font-normal">{"{EMAIL}"}</span>
            </div>
            <div className="mb-6">
              주소
              <br />
              <span className="font-normal">{"{Address}"}</span>
            </div>
            <div>
              우편주소
              <br />
              <span className="font-normal">{"{ZipCode}"}</span>
            </div>
          </div>
        </div>
        {/* 버튼 영역 */}
        <OrderButtons
          onModify={handleModify}
          onCancel={handleCancel}
          onOrder={handleOrder}
        />
      </div>
    </div>
  );
}
