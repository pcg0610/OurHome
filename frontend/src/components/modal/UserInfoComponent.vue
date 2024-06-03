<script setup>
    import { ref } from 'vue'
    import { useUserStore } from '@/stores/userStore'
    import UserInfoItemComponent from '@/components/modal/UserInfoItemComponent.vue'

    const userStore = useUserStore()
    const infoText = ref('')

    const addInfo = function() {
        console.log("add Info");
        userStore.addInfo(infoText.value)
        infoText.value = '';
    }
    
</script>

<template>
    <div>
        <v-row justify="center">
            <v-col cols="8" md="7">
                <v-text-field
                    density="compact"
                    placeholder="정보 입력"
                    prepend-inner-icon="mdi-pencil-outline"
                    variant="outlined"
                    v-model="infoText"
                    @keyup.enter="addInfo(infoText)"
                ></v-text-field>
            </v-col>

            <v-col cols="3">
                <v-btn class="me-4" type="submit" @click="addInfo(infoText)"> submit </v-btn>
            </v-col>
        </v-row>

        <v-row class="ms-3 d-flex justify-start">
            <UserInfoItemComponent v-for="info in userStore.items" :info="info"/>
        </v-row>
    </div>
</template>

<style scoped>

</style>