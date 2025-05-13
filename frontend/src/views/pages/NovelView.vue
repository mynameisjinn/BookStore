<script setup>
import { RouterLink } from 'vue-router';
import BreadCrumb from '../../components/BreadCrumb.vue'
import CategoryMenu from '../../components/CategoryMenu.vue'
import Tabs from '../../components/Tabs.vue';
import CardList from '../../components/CardList.vue'

const novelCategory = {
    name: '소설',
    link: '/category/novel',
    subCategories: [
        {
            title: '장르별',
            items: ['로맨스', 'SF', '추리/미스터리']
        },
        {
            title: '국가별',
            items: ['한국소설', '영미소설']
        }
    ]
}

const tabs = [
    { label: '전체', content: CardList },
    { label: '장르별', content: { template: '<div>소설 콘텐츠</div>' } },
    { label: '국가별', content: { template: '<div>컴퓨터/IT 콘텐츠</div>' } }
]

</script>
<template>
    <BreadCrumb />

    <!-- flex 컨테이너 추가 -->
    <div class="flex">
        <!-- 왼쪽: 카테고리 메뉴 -->
        <div class="w-1/4">
            <CategoryMenu :category="novelCategory" />
        </div>

        <!-- 오른쪽: 탭 컴포넌트 -->
        <div class="w-3/4">
            <Tabs  title="소설" :tabs="tabs" />
        </div>
    </div>
</template>
