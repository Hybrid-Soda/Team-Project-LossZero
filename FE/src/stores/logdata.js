import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useLogStore = defineStore("logdata", () => {
  const issue = ref(true);

  function createIssue() {
    // console.log(issue.value);
    issue.value = !issue.value;
  }

  return { issue, createIssue };
});
