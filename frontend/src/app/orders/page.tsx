"use client";

import { useSearchParams } from "next/navigation";
import { useOrder } from "./_hooks/useOrder";
import { useRouter } from "next/navigation";

// 주문자 정보 컴포넌트
function OrdererInfo({ orders }: { orders: { email: string; address: string; zipCode: string } }) {
  return (
    <section className="w-full md:w-1/3 flex flex-col justify-between text-lg h-full">
      <h2 className="text-2xl font-bold mb-8">주문자 정보</h2>
      <div className="mb-6">이메일<br /><span className="font-normal">{orders.email || "-"}</span></div>
      <div className="mb-6">주소<br /><span className="font-normal">{orders.address || "-"}</span></div>
      <div>우편주소<br /><span className="font-normal">{orders.zipCode || "-"}</span></div>
    </section>
  );
}

// 주문 상품 목록 컴포넌트
function OrderList({ cartItems }: { cartItems: { product: { productName: string; productPrice: number }; quantity: number }[] }) {
  return (
    <section className="w-full md:w-2/3">
      <h2 className="text-2xl font-bold mb-8">주문목록</h2>
      {cartItems.length > 0 ? (
        cartItems.map((item, idx) => (
          <div key={idx} className="text-xl mb-2">
            {item.product.productName} &nbsp; x {item.quantity}
          </div>
        ))
      ) : (
        <div className="text-gray-500">주문 상품이 없습니다.</div>
      )}
      <div className="text-xl font-semibold mt-8">
        총 금액 &nbsp; {cartItems.reduce((sum, item) => sum + item.product.productPrice * item.quantity, 0).toLocaleString()} 원
      </div>
    </section>
  );
}

export default function Page() {
  const searchParams = useSearchParams();
  const wishListId = searchParams.get("wishListId");
  const { orders, cartItems } = useOrder(wishListId);
  const router = useRouter();

  // 취소 버튼 클릭 시 localStorage 값 삭제 후 /products로 이동
  const handleCancel = () => {
    if (typeof window !== "undefined") {
      localStorage.removeItem("ordersInfo");
      localStorage.removeItem("cartItems");
    }
    router.push("/products");
  };

  // 수정 버튼 클릭 시 /products로 이동 (localStorage 값은 그대로)
  const handleEdit = () => {
    router.push("/products");
  };

  // 결제 버튼 클릭 시 임시 alert
  const handlePay = () => {
    alert("결제 기능은 추후 구현 예정입니다.");
  };

  return (
    <div className="min-h-screen flex flex-col items-center bg-gray-100 py-8">
      <h1 className="text-4xl font-bold text-center mb-8">Grids & Circles</h1>
      <div className="w-full max-w-4xl bg-gray-400 p-12 rounded-lg flex flex-col items-center">
        <div className="w-full bg-gray-50 p-10 rounded-lg flex flex-col md:flex-row justify-between mb-16" style={{ minHeight: 260 }}>
          <OrderList cartItems={cartItems} />
          <div className="w-8" />
          <OrdererInfo orders={orders} />
        </div>
        {/* 버튼 영역 */}
        <div className="flex gap-4 mt-4">
          <button
            className="px-6 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
            onClick={handleEdit}
          >
            수정
          </button>
          <button
            className="px-6 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 transition"
            onClick={handleCancel}
          >
            취소
          </button>
          <button
            className="px-6 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition"
            onClick={handlePay}
          >
            결제
          </button>
        </div>
      </div>
    </div>
  );
} 