<script setup>
    import { useUserStore } from '@/stores/userStore';
    const userStore = useUserStore()

    defineProps({
        info:String
    })
</script>

<template>
    <v-chip 
        class="me-2 mb-2" 
        append-icon="mdi-close-circle"
        size="default" 
        color="primary" 
        variant="elevated"
        @click.close="userStore.removeInfo(info)">
        
        {{ info }}
    </v-chip>

</template>

<style scoped>

</style>