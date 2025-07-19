"use client";

import { useSearchParams } from "next/navigation";
import { useOrder } from "./_hooks/useOrder";
import { useRouter } from "next/navigation";

const NEXT_PUBLIC_API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;
// 주문자 정보 컴포넌트
function OrdererInfo({
  orders,
}: {
  orders: { email: string; address: string; zipCode: string };
}) {
  return (
    <section className="w-full md:w-1/3 flex flex-col justify-between text-lg h-full">
      <h2 className="text-2xl font-bold mb-8">주문자 정보</h2>
      <div className="mb-6">
        이메일
        <br />
        <span className="font-normal">{orders.email || "-"}</span>
      </div>
      <div className="mb-6">
        주소
        <br />
        <span className="font-normal">{orders.address || "-"}</span>
      </div>
      <div>
        우편주소
        <br />
        <span className="font-normal">{orders.zipCode || "-"}</span>
      </div>
    </section>
  );
}

// 주문 상품 목록 컴포넌트
function OrderList({
  cartItems,
}: {
  cartItems: {
    product: { productName: string; productPrice: number };
    quantity: number;
  }[];
}) {
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
        총 금액 &nbsp;{" "}
        {cartItems
          .reduce(
            (sum, item) => sum + item.product.productPrice * item.quantity,
            0
          )
          .toLocaleString()}{" "}
        원
      </div>
    </section>
  );
}

export default function Page() {
  const searchParams = useSearchParams();
  const wishListId = searchParams.get("wishListId");
  const { orders, cartItems } = useOrder(wishListId);
  const router = useRouter();

  // 취소 버튼 클릭 시 localStorage 값 삭제 + db에 있는 wishList 삭제 후, /products로 이동
  const handleCancel = async () => {
    if (!wishListId) {
      alert("취소할 위시리스트 정보가 없습니다.");
      return;
    }

    try {
      const res = await fetch(
        `${NEXT_PUBLIC_API_BASE_URL}/api/v1/wishlist?wishListId=${wishListId}`,
        {
          method: "DELETE",
        }
      );

      if (!res.ok) {
        throw new Error("위시리스트 삭제 실패");
      }

      console.log("위시리스트 삭제 성공");

      if (typeof window !== "undefined") {
        localStorage.removeItem("ordersInfo");
        localStorage.removeItem("cartItems");
      }

      router.push("/products");
    } catch (error) {
      console.error("위시리스트 삭제 오류:", error);
      alert("주문 취소 중 오류가 발생했습니다.");
    }
  };

  // 수정 버튼 클릭 시 /products로 이동 (localStorage 값은 그대로)
  const handleEdit = () => {
    router.push(`/products?wishListId=${wishListId}`);
  };

  // 결제 버튼 클릭 시 /api/v1/orders로 wishListId 전송
  const handlePay = async () => {
    if (!wishListId) {
      alert("주문 정보가 없습니다.");
      return;
    }

    try {
      const res = await fetch(`${NEXT_PUBLIC_API_BASE_URL}/api/v1/orders`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ wishListId }),
      });

      if (!res.ok) {
        throw new Error("결제 처리 실패");
      }

      const responseData = await res.json();
      console.log("결제 응답:", responseData);
      alert("결제가 완료되었습니다!");

      // 결제 완료 후 localStorage 정리
      localStorage.removeItem("ordersInfo");
      localStorage.removeItem("cartItems");

      // 메인 페이지로 이동
      router.push("/products");
    } catch (error) {
      console.error("결제 실패:", error);
      alert("결제 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center bg-gray-100 py-8">
      <h1 className="text-4xl font-bold text-center mb-8">Grids & Circles</h1>
      <div className="w-full max-w-4xl bg-gray-400 p-12 rounded-lg flex flex-col items-center">
        <div
          className="w-full bg-gray-50 p-10 rounded-lg flex flex-col md:flex-row justify-between mb-16"
          style={{ minHeight: 260 }}
        >
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
