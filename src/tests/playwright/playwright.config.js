import { defineConfig } from '@playwright/test';

export default defineConfig({
  use: {
    baseURL: 'https://gearshare-tim-aight-1.onrender.com',
    //storageState: 'myGearShareAuth.json',
    video: 'on',
    trace: 'on',
    headless: false,
  },
  timeout: 30000,
  retries: 1,
});
